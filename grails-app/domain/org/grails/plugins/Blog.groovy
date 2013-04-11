package org.grails.plugins

class Blog {

	String url

	static belongsTo = [author: Author]

	static searchFields = [
		fields: [
			'url'
		],
		joins: [[name: "author", type: ""]]
	]
}
