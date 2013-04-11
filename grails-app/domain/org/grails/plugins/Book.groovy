package org.grails.plugins

class Book {

	String title
	String ISBN
	Date releaseDate

	static belongsTo = [author: Author]

	static searchFields = [
		fields: ['title', 'ISBN', 'releaseDate', 'author_name']
	]
}
