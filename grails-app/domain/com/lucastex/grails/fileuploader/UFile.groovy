package com.lucastex.grails.fileuploader

class UFile {

	Long size
	String path
	String name
	byte[] data
	String extension
	Date dateUploaded
	Integer downloads

    static constraints = {
		size(min:0L)
		path(nullable:true)
		data(nullable:true)
		name()
		extension()
		dateUploaded()
		downloads()
    }

	def afterDelete() {
		try {
			if(path) {
				File f = new File(path)
				if (f.delete()) {
					log.debug "file [${path}] deleted"
				} else {
					log.error "could not delete file: ${file}"
				}
			}
		} catch (Exception exp) {
			log.error "Error deleting file: ${e.message}"
			log.error exp
		}
	}
}