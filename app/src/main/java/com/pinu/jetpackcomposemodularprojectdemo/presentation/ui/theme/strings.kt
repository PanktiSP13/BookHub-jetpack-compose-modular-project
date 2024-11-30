package com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme

import com.pinu.domain.entities.network_service.response.BookResponse


const val dummyDescription =
    "Lorem Ipsum comes from sections 1.10.32 and 1.10.33 of \"de Finibus Bonorum et Malorum\" (The Extremes of Good and Evil) by Cicero, written in 45 BC. This book is a treatise on the theory of ethics, very popular during the Renaissance. The first line of Lorem Ipsum, \"Lorem ipsum dolor sit amet..\", comes from a line in section 1.10.32."


const val dummyString = "To apply the custom font across your app, you can set it as part of your app’s theme."
//const val dummyUrl = "https://img.etimg.com/photo/msid-98117645,imgsize-73706/TheAlchemistbyPauloCoelho.jpg"
const val dummyUrl = "https://m.media-amazon.com/images/I/81gRz9A4F6L._UF1000,1000_QL80_.jpg"
const val dummyBookTitle = "To apply the custom font across your app, you can set it as part of your app’s theme."
const val dummyBookDate ="12/05/2323"

val defaultBook = BookResponse.BookItemResponse(
    id = 1,
    name = "The White Tiger",
    imageUrl = "https://example.com/image.jpg",
    price = 554.0,
    availableQuantity = 15,
    genre = "Fiction",
    description = "A gripping tale.",
    authorName = "Aravind Adiga",
    bookPublishedDate = "2008-01-01",
    publishedYear = 2008,
    sellerName = "Book Store",
    numberOfPages = 320,
    isInCart = true,
    isFavourite = false
)