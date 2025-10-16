package com.example.listusertestapp.naviagtion

import android.os.Build
import android.os.Bundle
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import androidx.navigation.NavDestination
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.listusertestapp.data.model.UserInfo
import com.example.listusertestapp.presentation.DetailScreen
import com.example.listusertestapp.presentation.ListScreen
import com.example.listusertestapp.presentation.UserViewModel


/**
 * Created by Taranjeet Singh on 14/10/25.
 */
private val UserDataNavType = object : NavType<UserInfo>(isNullableAllowed = false) {

    // Convert the Parcelable to a string representation for the route
    @RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
    override fun put(bundle: Bundle, key: String, value: UserInfo) {
        bundle.putParcelable(key, value)
    }

    // Retrieve the Parcelable from the bundle
    override fun get(bundle: Bundle, key: String): UserInfo? {
        return bundle.getParcelable(key) as UserInfo?
    }

    // Converts the string argument from the route into the UserData object (unused for Parcelable)
    override fun parseValue(value: String): UserInfo {
        // This is primarily for query arguments. If you needed it, you'd use Gson here.
        throw UnsupportedOperationException("Parsing not supported for Parcelable NavType")
    }
}


@Composable
fun NavGraph(
    navHostController: NavHostController,
    viewModel: UserViewModel,
    innerPadding: PaddingValues
) {

    NavHost(
        navController = navHostController,
        startDestination = NavRoute.SplashScreen.path
    ) {


        addListScreen(navHostController,viewModel,innerPadding, this)

        addListDetailScreen(navHostController,viewModel, this)
    }

}


fun addListScreen(
    navHostController: NavHostController,
    viewModel: UserViewModel,
    innerPadding: PaddingValues,
    navGraphBuilder: NavGraphBuilder
) {

    navGraphBuilder.composable(route = NavRoute.SplashScreen.path) {

        ListScreen(navHostController, navGraphBuilder,innerPadding,viewModel) { userInfo: Bundle ->

            val graph: NavDestination? = navHostController.graph.findNode(NavRoute.DetailScreen.path
                .replace("{userData}", "placeholder"))

            val id: Int? = graph?.id
            navHostController.navigate(id!!,userInfo,null,null,)
        }

    }

}

fun addListDetailScreen(
    navHostController: NavHostController,
    viewModel: UserViewModel,
    navGraphBuilder: NavGraphBuilder
) {

    navGraphBuilder.composable(
        route = NavRoute.DetailScreen.path,
        arguments = listOf(
            navArgument("userData") {
                type = UserDataNavType
            }
        )) { backStackEntry ->

        val userData: UserInfo? = backStackEntry.
        arguments?.getParcelable<UserInfo>("userData")

        userData?.let {
            DetailScreen(navHostController, navGraphBuilder,it) {
                //navHostController.navigate(NavRoute.DetailScreen.path)
            }
        }


    }
}