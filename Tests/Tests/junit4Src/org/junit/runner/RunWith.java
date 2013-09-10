package org.junit.runner;

import org.robolectric.RobolectricTestRunner;

public @interface RunWith {

	Class<RobolectricTestRunner> value();

}
