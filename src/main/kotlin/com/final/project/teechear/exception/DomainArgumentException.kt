package com.final.project.teechear.exception

/**
 * EntityからDomainに変換する際に必要なカラムが不足している場合の例外クラス
 * @param message ログに出力するためのメッセージ
 * TODO messageを出力するためのLoggerを実装する
 */
class DomainArgumentException(message: String) : Exception()
