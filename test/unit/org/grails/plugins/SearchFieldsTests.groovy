package org.grails.plugins

import org.grails.plugins.Author;
import org.grails.plugins.Blog;
import org.grails.plugins.Book;
import org.grails.plugins.search.SearchEngine
import org.grails.plugins.search.util.SearchUtil


class SearchFieldsTests {
			
	void testSimple() {				
		def searhEngine = new SearchEngine(SearchUtil.extractConfig(Author))
		def search = searhEngine.createSearch()
		
		assert search.count == "select count(author) from Author author"
		assert search.query == "from Author author order by author.name"
		
		search = search.execute("name", "a")
		
		assert search.count == "select count(author) from Author author where(lower(author.name) like 'a%')"
		assert search.query == "from Author author where(lower(author.name) like 'a%') order by author.name"								
	}
		
	void testJoin() {
		def searhEngine = new SearchEngine(SearchUtil.extractConfig(Book))
		def search = searhEngine.createSearch()
		
		assert search.count == "select count(book) from Book book join book.author author"
		assert search.query == "from Book book join fetch book.author author"
														
		search = search.execute("title" , "test")
		
		assert search.count == "select count(book) from Book book join book.author author where(lower(book.title) like 'test%')"
		assert search.query == "from Book book join fetch book.author author where(lower(book.title) like 'test%')"
		
		search = search.execute("author_name" , "a")
		assert search.count == "select count(book) from Book book join book.author author where(lower(author.name) like 'a%')"
		assert search.query == "from Book book join fetch book.author author where(lower(author.name) like 'a%')"
				
		// force join
		searhEngine = new SearchEngine(SearchUtil.extractConfig(Blog))
		search = searhEngine.createSearch()
		
		assert search.count == "select count(blog) from Blog blog join author author"
		assert search.query == "from Blog blog join fetch author author"
		
		search = search.execute("url" , "http://search-fields-test.org")
		assert search.count == "select count(blog) from Blog blog join author author where(lower(blog.url) like 'http://search-fields-test.org%')"
		assert search.query == "from Blog blog join fetch author author where(lower(blog.url) like 'http://search-fields-test.org%')"
		
	}
	
	void testSort() {
		def searhEngine = new SearchEngine(SearchUtil.extractConfig(Book))
		def search = searhEngine.createSearch()
		def count = "select count(book) from Book book join book.author author"
		
		search.order = "author_name, title asc, ISBN desc"					
		assert search.query == "from Book book join fetch book.author author order by book.title asc, book.ISBN desc"
		assert search.count == count
		
		search.setSort("releaseDate", false)		
		assert search.query == "from Book book join fetch book.author author order by book.releaseDate desc"
		assert search.count == count
		
		search.setSort("author_name", true)
		assert search.query == "from Book book join fetch book.author author order by author.name asc"
		assert search.count == count
	} 

}
