package dev.seabat.android.hellobottomnavi.domain.entity

class RepositoryEntity(
    val name: String,
    val fullName: String,
    val htmlUrl: String,
    val description: String?,
    val createdAt: String,
    val owner: OwnerEntity
)
