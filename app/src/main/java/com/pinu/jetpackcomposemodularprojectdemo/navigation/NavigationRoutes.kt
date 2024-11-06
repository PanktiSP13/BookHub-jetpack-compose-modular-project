package com.pinu.jetpackcomposemodularprojectdemo.navigation


object NavRoutes {
    const val DASHBOARD_SCREEN = "dashboard-page"
    const val BOOK_LIST_SCREEN = "book-list-page"
    const val BOOK_DETAIL_SCREEN = "book-detail-screen"
    const val CART_SCREEN = "book-cart-screen"
}

object NavArguments {
    const val BOOK_ID = "bookId"
}


sealed class NavigationRoutes(val route: String) {

    data object DashboardScreen : NavigationRoutes(NavRoutes.DASHBOARD_SCREEN)

    data object BookListScreen : NavigationRoutes(NavRoutes.BOOK_LIST_SCREEN)

    data object BookDetailScreen :
        NavigationRoutes("${NavRoutes.BOOK_DETAIL_SCREEN}/{${NavArguments.BOOK_ID}}") {
        fun getBookDetail(bookId: Int) = "${NavRoutes.BOOK_DETAIL_SCREEN}/$bookId"
    }

    data object CartScreen : NavigationRoutes(NavRoutes.CART_SCREEN)


}