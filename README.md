# WikiImageSearch
wikipedia image search assignment

###Note
* request url : 
```
https://en.wikipedia.org/w/api.php?action=query&prop=pageimages&format=json&piprop=thumbnail&pithumbsize=500&pilimit=50&generator=prefixsearch&gpssearch=<query>
```
* param `pilimit` in url seems broken as it gets only 10 results for any value
* used staggered grid layout manager with 3 columns in `ImageSearchActivity`. can be configured to suit the needs
```
 StaggeredGridLayoutManager staggeredGridLayoutManager
                = new StaggeredGridLayoutManager(3, RecyclerView.VERTICAL);// staggered grid with 3 columns
```
* orientation change reloads the page. no specific implementation for orientation change.


###Screen shot
![screenshot](http://i.imgur.com/q8IMycp.png)
