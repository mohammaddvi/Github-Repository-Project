package com.example.githubrepositories

import com.example.githubrepositories.data.datasource.LocalProjectDataSource
import com.example.githubrepositories.data.datasource.LocalProjectsDataSourceImpl
import com.example.githubrepositories.data.datasource.RemoteProjectDataSource
import com.example.githubrepositories.data.repository.Project
import com.example.githubrepositories.data.repository.ProjectRepositoryImpl
import com.example.githubrepositories.data.repository.toProject
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.*
import kotlinx.coroutines.test.*
import org.junit.*

class ProjectRepositoryTest {

    @RelaxedMockK
    private lateinit var localProjectDataSource: LocalProjectDataSource

    @RelaxedMockK
    private lateinit var remoteProjectDataSource: RemoteProjectDataSource

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    private fun createRepository(
        localProjectDataSource: LocalProjectDataSource,
        remoteProjectDataSource: RemoteProjectDataSource,
    ) = ProjectRepositoryImpl(localProjectDataSource, remoteProjectDataSource)

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `when user needs data then we should get data from network`() =
        runTest {
            val repository = createRepository(FakeLocalDataSource(), FakeRemoteDataSource())
            val result = repository.fetchProjects(0, 10)
            Assert.assertEquals(fakeRemoteData.map { it.toProject() }, result)
        }

    @Test
    fun `when user needs data and api is failed then should get data from local when we have those data in local`() =
        runTest {
            coEvery { remoteProjectDataSource.fetchProjects(any(), any()) }.throws(
                Throwable(
                    IllegalAccessError::class.java.name
                )
            )
            coEvery {
                localProjectDataSource.fetchProjects(
                    any(),
                    any()
                )
            }.coAnswers { fakeLocalData }

            val repository = createRepository(localProjectDataSource, remoteProjectDataSource)

            val result = repository.fetchProjects(0, 10)
            Assert.assertEquals(fakeLocalData.map { it.toProject() }, result)
        }

    @Test
    fun `when user needs data and api is failed and there is not data in local then should show empty list`() =
        runTest {
            coEvery { remoteProjectDataSource.fetchProjects(any(), any()) }.throws(
                Throwable(
                    IllegalAccessError::class.java.name
                )
            )
            coEvery { localProjectDataSource.fetchProjects(any(), any()) }.coAnswers { emptyList() }

            val repository = createRepository(localProjectDataSource, remoteProjectDataSource)

            val result = repository.fetchProjects(0, 10)
            Assert.assertEquals(emptyList<Project>(), result)
        }

    @Test
    fun `when user needs data and api is successful and there is old data in local then should show updated data`() =
        runTest {
            val local = FakeLocalDataSource()

            Assert.assertEquals(fakeLocalData, local.fetchProjects(0, 10))
            val repository = createRepository(FakeLocalDataSource(), FakeRemoteDataSource())

            val result = repository.fetchProjects(0, 10)
            Assert.assertEquals(fakeRemoteData.map { it.toProject() }, result)
        }

}