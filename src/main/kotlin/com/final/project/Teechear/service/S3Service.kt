package com.final.project.Teechear.service

import com.amazonaws.AmazonClientException
import com.amazonaws.AmazonServiceException
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.model.ObjectMetadata
import com.amazonaws.services.s3.model.PutObjectRequest
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.multipart.MultipartFile

@Service
class S3Service {

    @Autowired
    private val s3client: AmazonS3? = null

    fun upload(multipartFile: MultipartFile): String {
        val fileName = if (multipartFile.originalFilename is String) multipartFile.originalFilename else "テストファイル.jpg" // TODO uniqな名前を生成するようにする
        try {
            s3client?.putObject(PutObjectRequest("teechear", fileName, multipartFile.inputStream, ObjectMetadata()))
        } catch (e: AmazonServiceException) {
            println(e.message)
            return String()
        } catch (e: AmazonClientException) {
            println(e.message)
            return String()
        }

        return "https://s3-ap-northeast-1.amazonaws.com/teechear/${fileName}"
    }
}