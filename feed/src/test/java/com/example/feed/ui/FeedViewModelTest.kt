package com.example.feed.ui

import androidx.paging.PagingData
import com.example.common.extension.DataState
import com.example.common.mvvm.BaseViewState
import com.example.feed.domain.use_case.GetArticlesUseCase
import com.example.feed.domain.use_case.GetSourcesUseCase
import com.example.feed.domain.use_case.GetTopHeadlinesUseCase
import com.example.model.dto.article.ArticleDto
import com.example.model.dto.sources.SourceDto
import com.example.storage.pref.PreferencesManager
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestCoroutineDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runBlockingTest
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertNotNull
import org.junit.Before
import org.junit.Test

class FeedViewModelTest {

    @MockK
    private lateinit var getTopHeadlinesUseCase: GetTopHeadlinesUseCase

    @MockK
    private lateinit var getSourcesUseCase: GetSourcesUseCase

    @MockK
    private lateinit var getArticlesUseCase: GetArticlesUseCase

    @RelaxedMockK
    private lateinit var preferencesManager: PreferencesManager

    private lateinit var viewModel: FeedViewModel
    private val testDispatcher = TestCoroutineDispatcher()


    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)

        viewModel = FeedViewModel(
            getTopHeadlinesUseCase,
            getSourcesUseCase,
            getArticlesUseCase,
            mockk(relaxed = true),
            preferencesManager
        )

    }

    @After
    fun teardown() {
        unmockkAll()
        Dispatchers.resetMain()
        testDispatcher.cleanupTestCoroutines()
    }

    @Suppress("MaxLineLength")
    @Test
    fun `loadTopHeadlines should update uiStateFeed with paged top headlines & should update uiState with BaseViewState`() =
        runTest {

            val expectedArticles =
                listOf(
                    ArticleDto("", "", "", "", "", "", ""),
                    ArticleDto(
                        "",
                        "", "", "", "", "", ""
                    )
                )
            val pagedTopHeadlines: Flow<PagingData<ArticleDto>> =
                flowOf(PagingData.from(expectedArticles))
            every { getTopHeadlinesUseCase(any()) } returns pagedTopHeadlines

            viewModel.onTriggerEvent(FeedEvent.LoadTopHeadlines)


            assertNotNull(viewModel.uiStateFeed.value.topHeadlines)
            assertEquals(viewModel.uiState.value, BaseViewState.Data)
        }

    @Test
    fun `when error happen loadTopHeadlines should update uiState with BaseViewState Error`() =
        runTest {

            val expectedError = RuntimeException("Error occurred")
            every { getTopHeadlinesUseCase(any()) } throws expectedError

            viewModel.onTriggerEvent(FeedEvent.LoadTopHeadlines)

            assertEquals(viewModel.uiState.value, BaseViewState.Error(expectedError))
            assertEquals(
                viewModel.uiStateFeed.value.topHeadlines,
                emptyFlow<PagingData<ArticleDto>>()
            )

        }

    @Suppress("MaxLineLength")
    @Test
    fun `when success loadArticles should update uiStateFeed with paged articles & update uiState with BaseViewState`() =
        runBlockingTest {

            val expectedArticles =
                listOf(
                    ArticleDto("", "", "", "", "", "", ""),
                    ArticleDto(
                        "",
                        "", "", "", "", "", ""
                    )
                )
            val selectedCategory = "All"
            val pagedArticles: Flow<PagingData<ArticleDto>> =
                flowOf(PagingData.from(expectedArticles))
            every { getArticlesUseCase(selectedCategory) } returns pagedArticles

            viewModel.onTriggerEvent(FeedEvent.LoadArticles)

            assertNotNull(viewModel.uiStateFeed.value.articles)
            assertEquals(viewModel.uiState.value, BaseViewState.Data)
        }

    @Test
    fun `when failed loadArticles should update uiState with BaseViewState Error`() = runTest {
        val expectedError = RuntimeException("Error occurred")
        val selectedCategory = "All"
        every { getArticlesUseCase(selectedCategory) } throws expectedError

        viewModel.onTriggerEvent(FeedEvent.LoadArticles)

        assertEquals(viewModel.uiState.value, BaseViewState.Error(expectedError))
        assertEquals(viewModel.uiStateFeed.value.articles, emptyFlow<PagingData<ArticleDto>>())
    }

    @Test
    fun `when success loadSources should update uiStateFeed with sources list & update uiState`() =
        runTest {
            val sourcesList = listOf(SourceDto("", ""), SourceDto("", ""))
            coEvery { getSourcesUseCase() } returns flowOf(DataState.Success(sourcesList))

            viewModel.onTriggerEvent(FeedEvent.LoadSourcesList)

            assertNotNull(viewModel.uiStateFeed.value.sourcesList)
            assertEquals(viewModel.uiState.value, BaseViewState.Data)
        }

    @Test
    fun `when failed loadSources should update uiState with Error`() = runTest {

        val expectedError = RuntimeException("Error occurred")
        coEvery { getSourcesUseCase() } throws expectedError

        viewModel.onTriggerEvent(FeedEvent.LoadSourcesList)

        assertEquals(viewModel.uiState.value, BaseViewState.Error(expectedError))
        assertEquals(viewModel.uiStateFeed.value.sourcesList, emptyList<List<SourceDto>>())
    }

    @Test
    fun `refreshScreen should call loadTopHeadlines, loadSources, and loadArticles in the expected sequence`() =
        runBlockingTest {
            val sourcesList = listOf(SourceDto("", ""), SourceDto("", ""))

            coEvery { getTopHeadlinesUseCase() } returns flowOf(PagingData.empty())
            coEvery { getSourcesUseCase() } returns flowOf(DataState.Success(sourcesList))
            coEvery { getArticlesUseCase(any()) } returns flowOf(PagingData.empty())

            viewModel.onTriggerEvent(FeedEvent.RefreshScreen)

            coVerify {
                getTopHeadlinesUseCase(any())
                getSourcesUseCase()
                getArticlesUseCase(any())
            }
        }

}
