package org.grails.plugins

class Blog {
	
	String url
			
	static belongsTo = [author: Author]
	
	static search = [
		fields: [
			'url'					
		],
		joins: [[name: "author"]]
	]
}
