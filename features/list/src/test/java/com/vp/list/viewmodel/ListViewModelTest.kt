package com.vp.list.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import com.vp.list.model.ListItem
import com.vp.list.model.SearchResponse
import com.vp.list.service.SearchService
import com.vp.list.viewmodel.ListState.ERROR
import com.vp.list.viewmodel.SearchResult.Companion.inProgress
import com.vp.list.viewmodel.SearchResult.Companion.success
import org.assertj.core.api.Assertions
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.mockito.ArgumentCaptor
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import org.mockito.internal.verification.AtLeast
import retrofit2.mock.Calls
import java.io.IOException

class ListViewModelTest {
    @get:Rule
    var instantTaskRule = InstantTaskExecutorRule()

    @Test
    fun shouldReturnErrorState() { //given
        val searchService = Mockito.mock(SearchService::class.java)
        Mockito.`when`(
            searchService.search(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyInt()
            )
        ).thenReturn(Calls.failure(IOException()))
        val listViewModel = ListViewModel(searchService)
        //when
        listViewModel.searchMoviesByTitle("title", 1)
        //then
        Assertions.assertThat(listViewModel.observeMovies().value?.listState)
            .isEqualTo(ERROR)
    }

    @Test
    fun shouldReturnInProgressState() { //given
        val searchService = Mockito.mock(SearchService::class.java)
        val searchResponse: SearchResponse = SearchResponse(" True", emptyList())
        Mockito.`when`(
            searchService.search(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyInt()
            )
        ).thenReturn(
            Calls.response(
                searchResponse
            )
        )
        val listViewModel = ListViewModel(searchService)
        val mockObserver =
            Mockito.mock(Observer::class.java) as Observer<SearchResult>
        listViewModel.observeMovies().observeForever(mockObserver)
        //when
        listViewModel.searchMoviesByTitle("title", 1)
        //then
        Mockito.verify(mockObserver)
            .onChanged(inProgress())
    }

    @Test
    fun shouldReturnSuccessState() { //given
        val searchService = Mockito.mock(SearchService::class.java)
        val listItem = ListItem("mock", "mock", "mock", "mock")
        val listItems: MutableList<ListItem> = ArrayList()
        listItems.add(listItem.copy())
        listItems.add(listItem.copy())
        val searchResponseMock = SearchResponse("True", listItems)

        Mockito.`when`(
            searchService.search(
                ArgumentMatchers.anyString(),
                ArgumentMatchers.anyInt()
            )
        ).thenReturn(Calls.response(searchResponseMock))

        val listViewModel = ListViewModel(searchService)
        val mockObserver =
            Mockito.mock(Observer::class.java) as Observer<SearchResult>
        val captor =
            ArgumentCaptor.forClass(
                SearchResult::class.java
            )
        listViewModel.observeMovies().observeForever(mockObserver)

        listViewModel.searchMoviesByTitle("title", 1)

        Mockito.verify(mockObserver, AtLeast(1)).onChanged(captor.capture())
        Assert.assertEquals(inProgress(), captor.allValues[0])
        Assert.assertEquals(
            success(listItems, listItems.size),
            captor.allValues[1]
        )
    }
}