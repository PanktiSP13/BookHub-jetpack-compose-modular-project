package com.pinu.jetpackcomposemodularprojectdemo.presentation.ui.theme

import com.pinu.domain.entities.network_service.response.BookResponse
import com.pinu.domain.entities.network_service.response.GenderType
import com.pinu.domain.entities.network_service.response.ProfileResponse


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

val dummyProfileResponse = ProfileResponse(
    success = true,
    message = "Profile loaded successfully",
    status = 200,
    data = ProfileResponse.ProfileData(
        id = "123",
        name = "John Doe",
        email = "john.doe@example.com",
        mobileNumber = "1234567890",
        gender = "male",
        profilePicUrl = "https://example.com/john_doe.jpg"
    )
)