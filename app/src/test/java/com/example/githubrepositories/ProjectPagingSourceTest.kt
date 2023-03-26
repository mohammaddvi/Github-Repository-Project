package com.example.githubrepositories

import androidx.paging.PagingSource
import com.example.githubrepositories.data.datasource.LocalProjectDataSource
import com.example.githubrepositories.data.datasource.RemoteProjectDataSource
import com.example.githubrepositories.data.repository.paging.ProjectPagingSource
import com.example.githubrepositories.data.repository.toProject
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test

class ProjectPagingSourceTest {
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

    private fun createProjectPagingSource(
    ) = ProjectPagingSource(remoteProjectDataSource, localProjectDataSource)


    @Test
    fun `when user needs data, then should get data from network`() = runTest {
        coEvery {
            remoteProjectDataSource.fetchProjects(any(), any())
        }.coAnswers {
            fakeRemoteData
        }

        val result = createProjectPagingSource().load(PagingSource.LoadParams.Refresh(1, 10, false))

        Assert.assertTrue(result is PagingSource.LoadResult.Page)
        Assert.assertEquals(
            fakeRemoteData.toProject(),
            (result as PagingSource.LoadResult.Page).data
        )
    }

    @Test
    fun `when user needs data and network is failed, then should get data from database`() =
        runTest {
            coEvery { remoteProjectDataSource.fetchProjects(any(), any()) }.throws(
                Throwable(
                    IllegalAccessError::class.java.name
                )
            )
            coEvery { localProjectDataSource.getAllProjects() }.coAnswers { fakeLocalData }

            val result =
                createProjectPagingSource().load(PagingSource.LoadParams.Refresh(1, 10, false))

            Assert.assertTrue(result is PagingSource.LoadResult.Page)
            Assert.assertEquals(
                fakeLocalData.map { it.toProject() },
                (result as PagingSource.LoadResult.Page).data
            )
        }

}