package org.grails.plugins


class Author {
	String name
	String lastName
	Date birthday
	String gender

	static constraints = {
		name maxSize: 60, blank: false, searchable: true
		lastName maxSize: 5, blank: false
		gender blank:false, inList:  ['F', 'M']
	}

	static searchFields = [
		fields : [
			'name',
			'lastName',
			'birthday',
		],
		defaultField : 'name',
		defaultSort : 'name'
	]
}
