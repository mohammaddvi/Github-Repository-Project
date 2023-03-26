package com.example.githubrepositories

import com.example.githubrepositories.data.datasource.LocalProjectDataSource
import com.example.githubrepositories.data.datasource.RemoteProjectDataSource
import com.example.githubrepositories.data.repository.ProjectRepositoryImpl
import com.example.githubrepositories.data.repository.paging.ProjectPagingSource
import com.example.githubrepositories.data.repository.paging.ProjectRemoteMediator
import com.example.githubrepositories.data.repository.toProjectEntity
import io.mockk.*
import io.mockk.impl.annotations.RelaxedMockK
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.*

class ProjectRepositoryTest {

    @RelaxedMockK
    private lateinit var remoteProjectDataSource: RemoteProjectDataSource

    @RelaxedMockK
    private lateinit var localProjectDataSource: LocalProjectDataSource

    @RelaxedMockK
    private lateinit var remoteMediator: ProjectRemoteMediator

    @RelaxedMockK
    private lateinit var pagingSource: ProjectPagingSource


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(Dispatchers.Unconfined)
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    private fun createRepository(
    ) = ProjectRepositoryImpl(
        remoteMediator,
        pagingSource,
        remoteProjectDataSource,
        localProjectDataSource,
    )


    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when user needs data then we should get data from network`() =
        runBlocking {
            coEvery {
                remoteProjectDataSource.fetchProjects(any(), any())
            }.coAnswers {
                fakeRemoteData
            }

            coEvery { localProjectDataSource.getAllProjects() }.coAnswers { fakeRemoteData.map { it.toProjectEntity() } }

            val repository = createRepository()
            val result = repository.getProjects(0, 10)
            Assert.assertEquals(fakeRemoteData.toProject(), result)
        }


    @Test
    fun `when user needs data and api is failed and there is not data in local then should show error`() =
        runTest {
            val error = Throwable(
                IllegalAccessError::class.java.name
            )
            coEvery { remoteProjectDataSource.fetchProjects(any(), any()) }.throws(
                error
            )
            coEvery {
                localProjectDataSource.getAllProjects()
            }.coAnswers { emptyList() }

            val repository = createRepository()

            val result = repository.getProjects(0, 10)
            Assert.assertEquals(Result.failure<List<Project>>(error), result)
        }
}