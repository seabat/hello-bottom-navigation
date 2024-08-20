package dev.seabat.android.hellobottomnavi.domain.entity

class QiitaArticleListEntity(val qiitas: ArrayList<QiitaArticleEntity>) :
    List<QiitaArticleEntity> by qiitas {
    fun get(title: String): QiitaArticleEntity? = qiitas.firstOrNull {
        it.title == title
    }
}