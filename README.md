# Daily News REST API Documentation

This document provides information about the endpoints available in the Daily News REST API.

## Table of Contents

- [Admin Controller](#admin-controller)
  - [Create Category](#create-category)

- [Comment Controller](#comment-controller)
  - [Add Comment](#add-comment)
  - [Delete Comment](#delete-comment)

- [News Controller](#news-controller)
  - [Get All News](#get-all-news)
  - [Get News by ID](#get-news-by-id)
  - [Get News by Tag](#get-news-by-tag)
  - [Get Trending News](#get-trending-news)
  - [Create News](#create-news)
  - [Update News](#update-news)
  - [Delete News](#delete-news)

- [User Controller](#user-controller)
  - [Register User](#register-user)
  - [Login User](#login-user)
  - [Reset Password](#reset-password)

---

## Admin Controller

### Create Category

- **Endpoint:** `POST /admin/category/create/{userId}`
- **Description:** Creates a new category.
- **Request Body:** JSON representation of the category.
- **Path Parameters:**
  - `userId` (Long) - User ID of the creator.
- **Response:** Returns the created category.

---

## Comment Controller

### Add Comment

- **Endpoint:** `POST /api/comments/add/{newsId}/{userId}`
- **Description:** Adds a new comment to a news article.
- **Request Body:** JSON representation of the comment.
- **Path Parameters:**
  - `newsId` (Long) - ID of the news article.
  - `userId` (Long) - User ID of the commenter.
- **Response:** Returns the added comment.

### Delete Comment

- **Endpoint:** `DELETE /api/comments/{commentId}/{userId}`
- **Description:** Deletes a comment by its ID.
- **Path Parameters:**
  - `commentId` (Long) - ID of the comment to delete.
  - `userId` (Long) - User ID of the commenter.
- **Response:** Returns a success message.

---

## News Controller

### Get All News

- **Endpoint:** `GET /api/news/newest`
- **Description:** Retrieves a list of all news articles.
- **Response:** Returns a list of news articles.

### Get News by ID

- **Endpoint:** `GET /api/news/{id}`
- **Description:** Retrieves a specific news article by its ID.
- **Path Parameters:**
  - `id` (Long) - ID of the news article.
- **Response:** Returns the requested news article.

### Get News by Tag

- **Endpoint:** `GET /api/news/by-tag/{tag}`
- **Description:** Retrieves news articles by a specific tag.
- **Path Parameters:**
  - `tag` (String) - Tag name to filter news articles.
- **Response:** Returns a list of news articles with the specified tag.

### Get Trending News

- **Endpoint:** `GET /api/news/trending`
- **Description:** Retrieves trending news articles.
- **Response:** Returns a list of trending news articles.

### Create News

- **Endpoint:** `POST /api/news/create/{userId}`
- **Description:** Creates a new news article.
- **Request Body:** JSON representation of the news article.
- **Path Parameters:**
  - `userId` (Long) - User ID of the news article creator.
- **Response:** Returns the created news article.

### Update News

- **Endpoint:** `PUT /api/news/{id}/{userId}`
- **Description:** Updates a news article by its ID.
- **Request Body:** JSON representation of the updated news article.
- **Path Parameters:**
  - `id` (Long) - ID of the news article to update.
  - `userId` (Long) - User ID of the updater.
- **Response:** Returns the updated news article.

### Delete News

- **Endpoint:** `DELETE /api/news/{id}`
- **Description:** Deletes a news article by its ID.
- **Path Parameters:**
  - `id` (Long) - ID of the news article to delete.
- **Response:** Returns a success message.

---

## User Controller

### Register User

- **Endpoint:** `POST /api/users/register`
- **Description:** Registers a new user.
- **Request Body:** JSON representation of the user.
- **Response:** Returns a success message along with the registered user's details.

### Login User

- **Endpoint:** `POST /api/users/login`
- **Description:** Logs in a user.
- **Request Body:** JSON representation of the user's credentials.
- **Response:** Returns a token for authenticated access or an error message.

### Reset Password

- **Endpoint:** `POST /api/users/reset-password`
- **Description:** Resets a user's password.
- **Request Parameters:**
  - `email` (String) - User's email address.
  - `newPassword` (String) - New password for the user.
- **Response:** Returns a success message or an error message.

---