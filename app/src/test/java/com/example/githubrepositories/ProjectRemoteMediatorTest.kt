package com.example.githubrepositories

import androidx.paging.*
import com.example.githubrepositories.data.datasource.LocalProjectDataSource
import com.example.githubrepositories.data.datasource.RemoteProjectDataSource
import com.example.githubrepositories.data.db.dao.RemoteKeysDao
import com.example.githubrepositories.data.repository.paging.ProjectRemoteMediator
import com.example.githubrepositories.data.repository.toProjectEntity
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.unmockkAll
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
@OptIn(ExperimentalPagingApi::class)

class ProjectRemoteMediatorTest {

    @RelaxedMockK
    private lateinit var localProjectDataSource: LocalProjectDataSource

    @RelaxedMockK
    private lateinit var remoteProjectDataSource: RemoteProjectDataSource

    @RelaxedMockK
    private lateinit var remoteKeysDao: RemoteKeysDao

    private lateinit var pagingState: PagingState<Int, Project>


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        pagingState = PagingState(
            listOf(),
            null,
            PagingConfig(10),
            10
        )
    }

    @After
    fun teardown() {
        unmockkAll()
    }

    private fun createRemoteMediator(
    ) = ProjectRemoteMediator(remoteProjectDataSource, localProjectDataSource, remoteKeysDao)

    @Test
    fun `when user needs more data and network provides data, then refresh load should be success and end of pagination should be false`() = runTest {
        coEvery { remoteProjectDataSource.fetchProjects(any(),any()) }.coAnswers { fakeRemoteData }

        val result = createRemoteMediator().load(LoadType.REFRESH, pagingState)

        Assert.assertTrue( result is RemoteMediator.MediatorResult.Success)
        Assert.assertFalse((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `when user needs more data and there is no more, then refresh load should be success and end of pagination should be true`() = runTest {
        coEvery { remoteProjectDataSource.fetchProjects(any(),any()) }.coAnswers { emptyList() }

        val result = createRemoteMediator().load(LoadType.REFRESH, pagingState)

        Assert.assertTrue( result is RemoteMediator.MediatorResult.Success)
        Assert.assertTrue((result as RemoteMediator.MediatorResult.Success).endOfPaginationReached)
    }

    @Test
    fun `when user needs data and network is failed, then refresh load should be error`() = runTest {
        coEvery { remoteProjectDataSource.fetchProjects(any(),any()) }.throws(Throwable(IllegalAccessError::class.java.name))

        val result = createRemoteMediator().load(LoadType.REFRESH, pagingState)

        Assert.assertTrue( result is RemoteMediator.MediatorResult.Error)
    }

}