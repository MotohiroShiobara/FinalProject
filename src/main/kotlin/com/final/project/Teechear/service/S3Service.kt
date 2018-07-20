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

    fun imageUpload(multipartFile: MultipartFile): String {
        if (!isImageConetntType(multipartFile.contentType)) {
            println("バグ発生")
            throw S3ServiceNotImage("画像以外のファイルをアップロードすることはできません")
        }

        val fileName = if (multipartFile.originalFilename is String) multipartFile.originalFilename else "テストファイル.jpg" // TODO uniqな名前を生成するようにする
        try {
            s3client?.putObject(PutObjectRequest("teechear", fileName, multipartFile.inputStream, ObjectMetadata()))
        } catch (e: AmazonServiceException) {
            return String()
        } catch (e: AmazonClientException) {
            return String()
        }

        return "https://s3-ap-northeast-1.amazonaws.com/teechear/${fileName}"
    }

    @Throws(S3ServiceNotImage::class)
    private fun isImageConetntType(contentType: String?): Boolean {
        when (contentType) {
            "image/png", "image/jpeg", "image/jpg" -> return true
            else -> return false
        }
    }

    class S3ServiceNotImage(s: String): Exception()
}