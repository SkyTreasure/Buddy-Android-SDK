package com.buddy.sdk.unittests;

import java.util.List;

import org.junit.Test;

import com.buddy.sdk.Receipt;
import com.buddy.sdk.Callbacks.OnCallback;
import com.buddy.sdk.StoreItem;
import com.buddy.sdk.responses.ListResponse;
import com.buddy.sdk.responses.Response;
import com.buddy.sdk.web.BuddyHttpClientFactory;

public class CommerceUnitTests extends BaseUnitTest {
    @Override
	public void setUp() throws Exception {
        super.setUp();

        createAuthenticatedUser();
    }

    @Test public void testReceiptSave() {
        String jsonResponse = readDataFromFileAsString("DataResponses/GenericSuccessResponse.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getCommerce().saveReceipt("1.23", 4, 5, "TestStoreName",
                new OnCallback<Response<Boolean>>() {
                    public void OnResponse(Response<Boolean> response, Object state) {
                        assertNotNull(response);
                        assertTrue(response.getResult());
                    }
                });
    }
    
    @Test public void testReceiptGetReceiptsForUser() {
        String jsonResponse = readDataFromFileAsString("DataResponses/CommerceUnitTests-GetReceipt.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getCommerce().getReceiptsForUser(null, null, new OnCallback<ListResponse<Receipt>>() {
                    public void OnResponse(ListResponse<Receipt> response, Object state) {
                        assertNotNull(response);
                        List<Receipt> receipts = response.getList();
                        assertNotNull(receipts);
                        
                        Receipt receipt = receipts.get(0);
                        assertEquals("TestStoreName", receipt.getStoreName());
                    }
                });
    }

    @Test public void testReceiptGetReceiptsForUserAndTransactionId() {
        String jsonResponse = readDataFromFileAsString("DataResponses/CommerceUnitTests-GetReceiptWithTransactionID.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        String customTransactionID = "42";
        testAuthUser.getCommerce().getReceiptForUserAndTransactionID(customTransactionID, null, 
                new OnCallback<ListResponse<Receipt>>() {
                    public void OnResponse(ListResponse<Receipt> response, Object state) {
                        assertNotNull(response);
                        List<Receipt> receipts = response.getList();
                        assertNotNull(receipts);
                        
                        Receipt receipt = receipts.get(0);
                        assertEquals("TestStoreName", receipt.getStoreName());
                    }
                });
    }

    @Test public void testStoreGetAllItems() {
        String jsonResponse = readDataFromFileAsString("DataResponses/CommerceUnitTests-GetAllItems.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getCommerce().getAllStoreItems(null, new OnCallback<ListResponse<StoreItem>>() {
                    public void OnResponse(ListResponse<StoreItem> response, Object state) {
                        assertNotNull(response);
                        List<StoreItem> storeItems = response.getList();
                        assertNotNull(storeItems);
                        assertTrue(storeItems.size() == 3);
                        
                        StoreItem storeItem = storeItems.get(2);
                        assertEquals(98, storeItem.getStoreItemID());
                    }
                });
    }

    @Test public void testStoreGetActiveItems() {
        String jsonResponse = readDataFromFileAsString("DataResponses/CommerceUnitTests-GetActiveItems.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getCommerce().getActiveStoreItems(null, new OnCallback<ListResponse<StoreItem>>() {
                    public void OnResponse(ListResponse<StoreItem> response, Object state) {
                        assertNotNull(response);
                        List<StoreItem> storeItems = response.getList();
                        assertNotNull(storeItems);
                        assertTrue(storeItems.size() == 2);
                        
                        StoreItem storeItem = storeItems.get(1);
                        assertEquals(97, storeItem.getStoreItemID());
                    }
                });
    }

    @Test public void testStoreGetFreeItems() {
        String jsonResponse = readDataFromFileAsString("DataResponses/CommerceUnitTests-GetFreeItems.json");
        BuddyHttpClientFactory.addDummyResponse(jsonResponse);

        testAuthUser.getCommerce().getFreeStoreItems(null, new OnCallback<ListResponse<StoreItem>>() {
            public void OnResponse(ListResponse<StoreItem> response, Object state) {
                assertNotNull(response);
                List<StoreItem> storeItems = response.getList();
                assertNotNull(storeItems);
                assertTrue(storeItems.size() == 1);
                
                StoreItem storeItem = storeItems.get(0);
                assertEquals(96, storeItem.getStoreItemID());
            }
        });
    }
}
