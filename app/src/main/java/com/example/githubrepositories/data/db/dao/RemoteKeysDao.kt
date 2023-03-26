package com.example.githubrepositories.data.db.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.githubrepositories.common.REMOTE_KEYS_TABLE_NAME
import com.example.githubrepositories.data.db.entities.RemoteKeysEntity

@Dao
interface RemoteKeysDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(remoteKeys: List<RemoteKeysEntity>)

    @Query("SELECT * FROM $REMOTE_KEYS_TABLE_NAME WHERE id= :repoId")
    suspend fun getRemoteKey(repoId: Long): RemoteKeysEntity?

    @Query("DELETE FROM $REMOTE_KEYS_TABLE_NAME")
    suspend fun clearRemoteKeys()
}