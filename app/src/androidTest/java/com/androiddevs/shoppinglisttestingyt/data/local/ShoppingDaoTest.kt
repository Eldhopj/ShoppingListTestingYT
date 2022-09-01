package com.androiddevs.shoppinglisttestingyt.data.local

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.test.filters.SmallTest
import com.androiddevs.shoppinglisttestingyt.getOrAwaitValue
import com.google.common.truth.Truth.assertThat
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import javax.inject.Inject
import javax.inject.Named

@ExperimentalCoroutinesApi
/**
@SmallTest Denotes all tests are unit test,
if its Instrumentation it should be @MediumTest, if UI its @LargeTest
NOTE: This filter can be given per test cases if all the test cases in a class doesn't fit in a single filter
Ref: https://i.stack.imgur.com/HfzyW.png
 */
@SmallTest

@HiltAndroidTest
class ShoppingDaoTest {

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    /**
     * This rules, makes all the functions in every test execute one after other in the same thread
     *      ie, like a suspend fn
     * */
    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    @Inject
    @Named("test_db") // if we didn't annotate with @Named, for test classes it will take the dependency from app module
    lateinit var database: ShoppingItemDatabase
    private lateinit var dao: ShoppingDao

    @Before
    fun setup() {
        hiltRule.inject() // makes inject dependencies
        dao = database.shoppingDao()
    }

    @After
    fun teardown() {
        database.close()
    }

    @Test
    fun insertShoppingItem() =
        runBlockingTest { // optimised for test cases, also have additional testing functionalities

            val shoppingItem = ShoppingItem("name", 1, 1f, "url", id = 1)
            dao.insertShoppingItem(shoppingItem)

            val allShoppingItems = dao
                .observeAllShoppingItems().getOrAwaitValue() //Converts live data synchronous

            assertThat(allShoppingItems).contains(shoppingItem)
        }

    @Test
    fun deleteShoppingItem() = runBlockingTest {
        val shoppingItem = ShoppingItem("name", 1, 1f, "url", id = 1)
        dao.insertShoppingItem(shoppingItem)
        dao.deleteShoppingItem(shoppingItem)

        val allShoppingItems = dao
            .observeAllShoppingItems().getOrAwaitValue() //Converts live data synchronous

        assertThat(allShoppingItems).doesNotContain(shoppingItem)
    }

    @Test
    fun observeTotalPriceSum() = runBlockingTest {
        val shoppingItem1 = ShoppingItem("name", 2, 10f, "url", id = 1)
        val shoppingItem2 = ShoppingItem("name", 4, 5.5f, "url", id = 2)
        val shoppingItem3 = ShoppingItem("name", 0, 100f, "url", id = 3)
        dao.insertShoppingItem(shoppingItem1)
        dao.insertShoppingItem(shoppingItem2)
        dao.insertShoppingItem(shoppingItem3)

        val totalPriceSum =
            dao.observeTotalPrice().getOrAwaitValue() // Converts live data synchronous

        assertThat(totalPriceSum).isEqualTo(2 * 10f + 4 * 5.5f)
    }
}













