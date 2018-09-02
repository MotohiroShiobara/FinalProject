package com.final.project.teechear.exception

/**
 * 存在しないページを指定された場合に返すException
 * Controller側では404.htmlを返すことを期待している
 */
class PageNotFoundException(message: String): Exception()
