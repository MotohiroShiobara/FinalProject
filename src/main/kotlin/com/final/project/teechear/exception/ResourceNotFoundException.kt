package com.final.project.teechear.exception

/**
 * Resourceが見つからない場合の例外クラス
 * @param message ログに出力するためのメッセージ
 * TODO messageを出力するためのLoggerを実装する
 */
class ResourceNotFoundException(message: String) : Exception()
