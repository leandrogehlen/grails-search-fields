SearchFields
====================

Auto-generate HQL query based on DSL in domain classes

Usage
-----

Definition of domain class:

```groovy
class Author {
    String name
    String lastName
    Date birthday

    static searchFields = [
        fields : [
            'name',
            'lastName',
            'birthday'
        ],
        defaultField : 'name', //default filter field
  	    defaultSort : 'name'
    ]
}

```
Example of use in action "list":

```groovy
def list(Integer max) {
    params.max = Math.min(max ?: 10, 100)
    params.sort = 'name'
    params.order = 'asc'
    params.field = 'lastName'
    params.value = 'test'

    def search = Author.createSearch()
    search.setSort(params.sort, params.order == 'asc')
    search = search.execute(params.field, params.value)

    //generate: select count(author) from Author author where (lower(author.lastName) like 'test%)
    def count = Author.executeQuery(search.count)

    //generate: from Author author where(lower(author.lastName) like 'test%') order by author.name asc
    def list = Author.findAll(search.query, [max: params.max, offset: params.offset])

    [authorInstanceList: list, authorInstanceTotal: count]
}
```