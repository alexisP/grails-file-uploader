package com.lucastex.grails.fileuploader

class DownloadController {
	
	def messageSource
	
    def index = { 
	
		UFile ufile = UFile.get(params.id)
		if (!ufile) {
			def msg = messageSource.getMessage("fileupload.download.nofile", [params.id] as Object[], request.locale)
			log.debug msg
			flash.message = msg
			redirect controller: params.errorController, action: params.errorAction
			return
		}
		
		def file
		if(ufile.path) {
			file = new File(ufile.path)
		}
		if (file?.exists() || ufile.path == null) {
			log.debug "Serving file id=[${ufile.id}] for the ${ufile.downloads} to ${request.remoteAddr}"
			ufile.downloads++
			ufile.save()
			response.setContentType("application/octet-stream")
			response.setHeader("Content-disposition", "${params.contentDisposition}; filename=\"${ufile.name}\"")
			if(file?.exists()) {
				response.outputStream << file.bytes
			}
			else {
				response.outputStream << ufile.data
			}
			return
		} else {
			def msg = messageSource.getMessage("fileupload.download.filenotfound", [ufile.name] as Object[], request.locale)
			log.error msg
			flash.message = msg
			redirect controller: params.errorController, action: params.errorAction
			return
		}
	}
}
