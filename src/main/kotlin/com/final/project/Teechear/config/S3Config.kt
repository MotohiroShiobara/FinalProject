package com.final.project.Teechear.config

import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.regions.Regions
import com.amazonaws.services.s3.AmazonS3
import com.amazonaws.services.s3.AmazonS3ClientBuilder
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class S3Config(private val appSettings: AppSettings) {

    private val awsId: String = appSettings.get("aws.access-key")


    private val awsKey: String? = appSettings.get("aws.secret-key")

    @Value("ap-northeast-1")
    private val region: String? = null

    @Bean
    fun s3client(): AmazonS3 {

        val awsCreds = BasicAWSCredentials(awsId!!, awsKey!!)

        return AmazonS3ClientBuilder.standard()
                .withRegion(Regions.fromName(region))
                .withCredentials(AWSStaticCredentialsProvider(awsCreds))
                .build()
    }
}