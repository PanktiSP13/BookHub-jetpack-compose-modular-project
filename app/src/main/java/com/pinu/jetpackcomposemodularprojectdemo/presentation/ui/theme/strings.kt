package com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme

import com.pinu.domain.entities.network_service.response.BookResponse


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