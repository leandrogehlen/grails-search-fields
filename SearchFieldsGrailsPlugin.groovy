import org.grails.plugins.search.SearchEngine
import org.grails.plugins.search.util.SearchUtil
import org.springframework.orm.hibernate3.HibernateTemplate

class SearchFieldsGrailsPlugin {
	
    def version = "0.1"    
    def grailsVersion = "2.2 > *"        

    def title = "Grails Search Fields Plugin"
    def author = "Leandro Guindani Gehlen"
    def authorEmail = "leandrogehlen@gmail.com"
    def description = "Auto-generate search query based on DSL in domain classes"
    def documentation = "https://github.com/leandrogehlen/grails-search-fields/wiki"
	
	def license = "APACHE"

    def issueManagement = [system: "github", url: "https://github.com/leandrogehlen/grails-search-fields/issues"]
    def scm = [ url: "https://github.com/leandrogehlen/grails-search-fields" ]
   
    def doWithDynamicMethods = { ctx ->
		application.domainClasses.each { domainClass ->																					
			domainClass.metaClass.static.createSearch = {
				def searchEngine = new SearchEngine(SearchUtil.extractConfig(delegate))
				return searchEngine.createSearch()				
			}
		}
    }
}
