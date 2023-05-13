package com.example.harganow.presentation.navigation

import LoginScreen
import RegisterScreen
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.Icon
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountCircle
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.List
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.outlined.AccountCircle
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.outlined.List
import androidx.compose.material.icons.outlined.Notifications
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.toUpperCase
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.TextUnitType
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.harganow.domain.model.Address
import com.example.harganow.domain.model.ItemPrice
import com.example.harganow.presentation.announcement.AnnouncementScreen
import com.example.harganow.presentation.cart.CartScreen
import com.example.harganow.presentation.checkout.CheckOutScreen
import com.example.harganow.presentation.itemGroup.ItemGroupScreen
import com.example.harganow.presentation.login.HomeScreen
import com.example.harganow.presentation.main.MainScreen
import com.example.harganow.presentation.order.OrderScreen
import com.example.harganow.presentation.productDetail.ProductDetailScreen
import com.example.harganow.presentation.search.SearchScreen
import com.example.harganow.presentation.user.*

@Composable
fun MyApp() {
    val navController = rememberNavController()

    val checkOutData: MutableList<Map<ItemPrice, Int>> = remember { mutableListOf() }
    val addressData = remember { mutableStateOf<Address>(Address()) }
    val bottomBarState = rememberSaveable { mutableStateOf(false) }

    val items = listOf(
        Screen.Home, Screen.Order, Screen.Announcement, Screen.Profile
    )

    fun handleBack() {
        checkOutData.clear()
        navController.popBackStack()
    }

    fun updateBottomBarState(currentDestination: String) {
        bottomBarState.value = !bottomBarState.value
        val notShowBottomBarList = arrayListOf<String>(
            "home", "login", "register", "policy", "checkout", "cart", "search", "null"
        )

        // Check if current destination route in not show bottom bar list
        bottomBarState.value = !notShowBottomBarList.contains(currentDestination)
    }

    val iconList: List<ImageVector> = listOf(
        Icons.Filled.Home, Icons.Filled.List, Icons.Filled.Notifications, Icons.Filled.AccountCircle
    )

    val unFocusIconList: List<ImageVector> = listOf(
        Icons.Outlined.Home,
        Icons.Outlined.List,
        Icons.Outlined.Notifications,
        Icons.Outlined.AccountCircle
    )


    Scaffold(
        bottomBar = {
            updateBottomBarState(navController.currentDestination?.route.toString())
            if (bottomBarState.value) BottomNavigation {
                val navBackStackEntry by navController.currentBackStackEntryAsState()
                val currentDestination = navBackStackEntry?.destination

                items.forEach { screen ->
                    BottomNavigationItem(icon = {
                        Icon(
                            if (currentDestination?.hierarchy?.any { it.route == screen.route } == true)
                                iconList[screen.icon]
                            else
                                unFocusIconList[screen.icon],
                            contentDescription = screen.label
                        )
                    },
                        label = {
                            Text(
                                text = screen.label,
                                fontSize = 2.em,
                            )
                        },
                        selected = currentDestination?.hierarchy?.any { it.route == screen.route } == true,
                        onClick = {
                            navController.navigate(screen.route) {
                                // Pop up to the start destination of the graph to
                                // avoid building up a large stack of destinations
                                // on the back stack as users select items
                                popUpTo(navController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                // Avoid multiple copies of the same destination when
                                // reselecting the same item
                                launchSingleTop = true
                                // Restore state when reselecting a previously selected item
                                restoreState = true
                            }
                        })
                }
            }
        },
    ) { innerPadding ->
        NavHost(navController, startDestination = "home", Modifier.padding(innerPadding)) {
            composable("home") {
                HomeScreen(
                    navigateToLogin = { navController.navigate("login") },
                    navigateToRegister = { navController.navigate("register") },
                )
            }
            composable("login") {
                LoginScreen(
                    navigateToPreviousStack = { navController.popBackStack() },
                    navigateToRegister = { navController.navigate("register") },
                    // TODO: Change this to navigate to the main screen
                    navigateToMain = {
                        navController.apply {
                            popBackStack()
                            navigate("main")
                        }
                    },
                )
            }
            composable("register") {
                RegisterScreen(
                    navigateToPreviousStack = { navController.popBackStack() },
                    navigateToLogin = { navController.navigate("login") },
                )
            }
            composable("user_settings") {
                UserSettingsScreen(
                    navigateToPreviousStack = { navController.popBackStack() },
                    navigateToManageAccount = { navController.navigate("manage_account") },
                    navigateToAddress = {},
                    navigateToBankAccount = { navController.navigate("bank_accounts_cards") },
                    navigateToPolicy = { navController.navigate("policy") },
                    navigateToHome = { navController.navigate("home") },
                )
            }
            composable("policy") {
                PolicyScreen(
                    navigateToPreviousStack = { navController.popBackStack() },
                )
            }
            composable("manage_account") {
                ManageAccountScreen(navigateToPreviousStack = { navController.popBackStack() },
                    navigateToForgetPassword = { navController.navigate("forget_password") })
            }
            composable("reset_password") {
                ResetPasswordScreen(
                    navigateToPreviousStack = { navController.popBackStack() },
                    navigateToForgetPassword = { navController.navigate("forget_password") },
                )
            }
            composable("forget_password") {
                ForgetPasswordScreen(
                    navigateToPreviousStack = { navController.popBackStack() },
                )
            }
            composable("announcement") {
                AnnouncementScreen(
                    navigateToPreviousStack = { navController.popBackStack() },
                )
            }
            composable("cart") {
                CartScreen(
                    navigateToPreviousStack = { navController.popBackStack() },
                    navigateToCheckout = { navController.navigate("checkout") },
                    chosenList = checkOutData,
                )
            }
            composable("checkout") {
                CheckOutScreen(
                    navController = navController,
                    chosenList = checkOutData,
                    address = addressData
                )
            }
            composable("order") {
                OrderScreen(
                    navigateToPreviousStack = { navController.popBackStack() },
                    navigateToCheckOut = { navController.navigate("checkout") },
                )
            }
            composable("bank_accounts_cards") {
                BankAccountsCardsScreen(
                    navigateToPreviousStack = { navController.popBackStack() },
                    navigateToAddBankAccount = { },
                    navigateToAddCard = { },
                )
            }
            composable("main") {
                MainScreen(
                    navigateToCart = { navController.navigate("cart") },
                    navigateToProductDetail = { navController.navigate("product_detail") },
                    navigateToItemGroupScreen = { navController.navigate("item_group") },
                    navigateToSearch = { navController.navigate("search") },
                )
            }
            composable("item_group") {
                ItemGroupScreen(
                    navigateToPreviousStack = { navController.popBackStack() },
                    navigateToProductDetail = { navController.navigate("product_detail") },
                    navigateToCart = { navController.navigate("cart") },
                    navigateToSearch = { navController.navigate("search") },
                )
            }
            composable("product_detail") {
                ProductDetailScreen(
                    navigateToPreviousStack = { navController.popBackStack() },
                    navigateToProductDetail = { navController.navigate("product_detail") },
                    navigateToCart = { navController.navigate("cart") },
                )
            }
            composable("search") {
                SearchScreen(
                    navigateToPreviousStack = { navController.popBackStack() },
                    navigateToProductDetail = { navController.navigate("product_detail") },
                    navigateToCart = { navController.navigate("cart") },
                )
            }
            composable("address") {
                AddressScreen(
                    navigateToPreviousStack = { navController.navigate("checkout") },
                    address = addressData
                )
            }
        }
    }
}
