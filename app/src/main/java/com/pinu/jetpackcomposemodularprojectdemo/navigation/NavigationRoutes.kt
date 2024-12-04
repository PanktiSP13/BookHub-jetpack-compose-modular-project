package com.pinu.jetpackcomposemodularprojectdemo.navigation


object NavRoutes {
    const val DASHBOARD_SCREEN = "dashboard-page"
    const val BOOK_LIST_SCREEN = "book-list-page"
    const val BOOK_DETAIL_SCREEN = "book-detail-screen"
    const val CART_SCREEN = "book-cart-screen"
    const val PROFILE_SCREEN = "profile-screen"
}

object NavArguments {
    const val BOOK_ID = "bookId"
}


sealed class NavigationRoutes(val route: String) {

    data object DashboardScreen : NavigationRoutes(NavRoutes.DASHBOARD_SCREEN)

    data object BookListScreen : NavigationRoutes(NavRoutes.BOOK_LIST_SCREEN)

    data object BookDetailScreen :
        NavigationRoutes("${NavRoutes.BOOK_DETAIL_SCREEN}?${NavArguments.BOOK_ID}={${NavArguments.BOOK_ID}}") {
        fun getBookDetail(bookId: Int?=null): String {
            return if (bookId == null) {
                NavRoutes.BOOK_DETAIL_SCREEN
            } else {
                "${NavRoutes.BOOK_DETAIL_SCREEN}?${NavArguments.BOOK_ID}=$bookId"
            }
        }
    }

    /*    data object BookDetailScreen :
            NavigationRoutes("${NavRoutes.BOOK_DETAIL_SCREEN}?${NavArguments.BOOK_ID}={${NavArguments.BOOK_ID}}") {
            fun getBookDetail(bookId: Int? = null): String {
                return if (bookId != null) {
                    "${NavRoutes.BOOK_DETAIL_SCREEN}?${NavArguments.BOOK_ID}=$bookId"
                } else {
                    NavRoutes.BOOK_DETAIL_SCREEN
                }
            }
        }*/

    data object CartScreen : NavigationRoutes(NavRoutes.CART_SCREEN)

    data object ProfileScreen : NavigationRoutes(NavRoutes.PROFILE_SCREEN)


}