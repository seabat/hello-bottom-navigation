package dev.seabat.android.hellobottomnavi.domain.entity

class RepositoryListEntity(val list: ArrayList<RepositoryEntity>) : List<RepositoryEntity> by list {
    fun get(name: String): RepositoryEntity? = list.firstOrNull {
        it.name == name
    }
}