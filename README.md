📰 NewsApp
NewsApp is a modern, feature-rich news application built using Kotlin and adheres to the MVVM architecture. The app provides a seamless and intuitive experience for browsing, searching, and bookmarking news articles. It integrates endless scrolling for a continuous flow of information and allows users to view full articles directly within the app using a WebView.

✨ Features
1. Browsing News
Fetches a list of news article headlines with accompanying images and published dates.
Provides a visually appealing and user-friendly interface for staying updated with the latest news.
2. Searching News
Allows users to search for news articles based on specific keywords.
Displays relevant articles with headlines, images, and publication details.
3. Bookmarking
Enables users to save their favorite articles for offline access.
Integrates Room Database to store and manage bookmarked articles persistently.
4. Endless Scrolling
Implements modern endless scrolling for a continuous feed of news articles using Pagination.
Each news card includes:
Image: A thumbnail representing the article.
Headline: A concise summary of the article.
Description: A brief snippet of the article content.
Clicking on a news card navigates to the full article via an in-app WebView displaying the original publication.

🛠️ Tech Stack
Frontend:
Kotlin: For a clean, concise, and safe programming experience.
Jetpack Compose (Optional): To build UI components with a modern toolkit.
Architecture:
MVVM (Model-View-ViewModel): Ensures separation of concerns and makes the app scalable and testable.
Libraries and Tools:
Room Database: For managing and persisting bookmarked articles.
Retrofit: For handling API requests to fetch news articles.
Coil: For loading and displaying images efficiently.
Pagination 3: For implementing endless scrolling.
WebView: To display full articles directly within the app.
Hilt: For dependency injection (if used).
Coroutines: For asynchronous operations.

🖥️ Screenshots
![OnBoarding 1](https://github.com/user-attachments/assets/ae78138d-202d-4e8b-b147-876428c4cab4)
![OnBoarding 2](https://github.com/user-attachments/assets/73ad9f5f-1a6b-4bfc-ba99-413418127e5e)
![OnBoarding 3](https://github.com/user-attachments/assets/050469e6-6e60-4976-8f89-f7b99cb859aa)
![HomeScreen](https://github.com/user-attachments/assets/7b675fa4-1339-4361-a8e7-b155433cf67b)
![Search Screen](https://github.com/user-attachments/assets/38bb0646-70d6-4968-a9c7-e9e8eaa2cd9f)
![Bookmark](https://github.com/user-attachments/assets/c930b6eb-c54f-4d37-83c7-7759dcc51a5b)
![NewsScroll Screen](https://github.com/user-attachments/assets/91d7b658-968a-4b3b-bf46-50babc1e4d62)
![WebView Screen](https://github.com/user-attachments/assets/37030abe-6886-4422-b58f-4b2d5ee8e4a4)




📖 How It Works
 - Home Screen: Displays the latest news articles with headlines, images, and published dates.
 - Search Functionality: Type a keyword to fetch a list of relevant articles.
 - Bookmarking: Save your favorite articles for offline reading.
 - Endless Scrolling: Keep scrolling for a continuous feed of articles.
 - Full Article View: Click on an article to open it in an in-app WebView.

🛠️ Installation and Setup
Clone the repository:
bash
Copy code
 - git clone https://github.com/Lalit-Dhembre/NewsApp.git
 - Open the project in Android Studio.
 - Add your API Key in the appropriate configuration file (e.g., Constants.kt).
 - Build and run the app on your Android device/emulator.

🚀 Future Enhancements
Offline mode to cache articles for reading without an internet connection.
Push notifications for breaking news.
Dark mode for a better reading experience.

🤝 Contributing
Contributions are always welcome! Feel free to open issues or submit pull requests for improvements.

