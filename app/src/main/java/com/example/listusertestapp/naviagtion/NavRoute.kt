package com.example.listusertestapp.naviagtion

/**
 * Created by Taranjeet Singh on 14/10/25.
 */
sealed class NavRoute(val path: String) {

    object SplashScreen : NavRoute("SplashScreen")

    object ListScreen : NavRoute("ListScreen")

    object DetailScreen : NavRoute("DetailScreen")

}