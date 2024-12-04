NewsFresh
Thank you for choosing NewsFresh - free Android application for technology lovers. You will never miss your favorite news thanks to our mobile app, intuitive UI and up-to-the-minute notifications.

Features
- Tech News: Features the latest tech disruptors on the site.
- Intuitive bulleted layout: With engaging headlines and illustrations, we make the process smooth from searching an article to reading it.
- Chrome Custom Tabs: Access news articles directly in the Chrome application without additional input.
- Modern and light weight application: Allows for an easy and minimalistic experience.

How It Works
NewsFresh fetches news articles using the NewsAPI, parses the data, and displays it in a sleek, scrollable list. Clicking on a news item takes you to the original article using Chrome Custom Tabs.

Project Structure
Once again, let's review the structure of the project:
- MainActivity: In charge of loading dat y from the server to the recyclerview for the system’s first use.
- NewsListAdapter: Displays posts with fixtures in them to every holder in the recycler view's custom esView.
- NewsItemClicked: Event within a news repository that tracks clicks made on the repository's items.
- MySingleton: Enables network requests to be made using Volley and does so alone maintaining persistence attention on singularity.
News: This is a class containing a data model for news articles.
