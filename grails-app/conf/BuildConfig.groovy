grails.project.class.dir = "target/classes"
grails.project.test.class.dir = "target/test-classes"
grails.project.test.reports.dir = "target/test-reports"

grails.project.dependency.resolution = {

	inherits "global"
	log "warn"

	repositories {
		grailsCentral()
	}

	plugins {
		build(":release:2.2.0", ":rest-client-builder:1.0.3") {
			export = false
		}

		compile(":hibernate:$grailsVersion") {
			export = false
		}
	}
}