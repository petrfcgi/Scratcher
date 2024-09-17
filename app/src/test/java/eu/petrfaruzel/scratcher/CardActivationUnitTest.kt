package eu.petrfaruzel.scratcher

import de.jensklingenberg.ktorfit.Response
import eu.petrfaruzel.scratcher.config.Configuration
import eu.petrfaruzel.scratcher.core.data.DataRequestResult
import eu.petrfaruzel.scratcher.features.activation.data.ActivationApi
import eu.petrfaruzel.scratcher.features.activation.data.VersionDTO
import eu.petrfaruzel.scratcher.features.activation.domain.ActivationRepository
import eu.petrfaruzel.scratcher.features.shared.data.CardDS
import io.ktor.client.statement.HttpResponse
import io.ktor.http.HttpStatusCode
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Test
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin
import org.koin.dsl.module
import java.util.UUID

class CardActivationUnitTest {

    private val mockApi = mockk<ActivationApi>()
    private val mockCardDS = mockk<CardDS>(relaxed = true) // relaxed = true to avoid manual stubbing
    private lateinit var activationRepository: ActivationRepository
    private val mockHttpResponse = mockk<HttpResponse>(relaxed = true)

    @Before
    fun setup() {
        startKoin {
            modules(
                module {
                    single { mockApi }
                    single { mockCardDS }
                }
            )
        }

        activationRepository = ActivationRepository(mockApi, mockCardDS)
        every { mockHttpResponse.status } returns HttpStatusCode.OK
    }

    @After
    fun tearDown() {
        stopKoin()
    }

    @Test
    fun `activateCard returns success and activates card when android version is equal min supported api version`() = runTest {
        // Arrange
        val androidApiVersion = (Configuration.MIN_SUPPORTED_API_VERSION).toString()
        val versionDto = VersionDTO(android = androidApiVersion, ios = "15.0")
        val mockResponse = Response.success(versionDto, mockHttpResponse) as Response<VersionDTO>

        coEvery { mockApi.getVersion(any()) } returns mockResponse

        // Act
        val result = activationRepository.activateCard(UUID.randomUUID().toString())

        // Assert
        assert(result is DataRequestResult.Success)
        Assert.assertEquals(versionDto, (result as DataRequestResult.Success).value)

        // Verify that setIsActivated is called with 'true' since the version is above 277028
        coVerify { mockCardDS.setIsActivated(true) }
    }

    @Test
    fun `activateCard returns failure when android version is below min supported api version`() = runTest {
        // Arrange
        val androidApiVersion = (Configuration.MIN_SUPPORTED_API_VERSION - 1).toString()
        val versionDto = VersionDTO(android = androidApiVersion, ios = "15.0")
        val mockResponse = Response.success(versionDto, mockHttpResponse) as Response<VersionDTO>

        coEvery { mockApi.getVersion(any()) } returns mockResponse

        // Act
        val result = activationRepository.activateCard("some_code")

        // Assert
        assert(result is DataRequestResult.Failure)

        // Verify that setIsActivated is NOT called
        coVerify(exactly = 0) { mockCardDS.setIsActivated(true) }
    }

    @Test
    fun `activateCard returns failure when android version is above min supported api version`() = runTest {
        // Arrange
        val androidApiVersion = (Configuration.MIN_SUPPORTED_API_VERSION + 1).toString()
        val versionDto = VersionDTO(android = androidApiVersion, ios = "15.0")
        val mockResponse = Response.success(versionDto, mockHttpResponse) as Response<VersionDTO>

        coEvery { mockApi.getVersion(any()) } returns mockResponse

        // Act
        val result = activationRepository.activateCard(UUID.randomUUID().toString())

        // Assert
        assert(result is DataRequestResult.Success)
        Assert.assertEquals(versionDto, (result as DataRequestResult.Success).value)

        // Verify that setIsActivated is called with 'true' since the version is above 277028
        coVerify { mockCardDS.setIsActivated(true) }
    }

    @Test
    fun `activateCard returns failure when android version is not a number`() = runTest {
        // Arrange
        val versionDto = VersionDTO(android = "android15", ios = "15.0")
        val mockResponse = Response.success(versionDto, mockHttpResponse) as Response<VersionDTO>

        coEvery { mockApi.getVersion(any()) } returns mockResponse

        // Act
        val result = activationRepository.activateCard("some_code")

        // Assert
        assert(result is DataRequestResult.Failure)

        // Verify that setIsActivated is NOT called
        coVerify(exactly = 0) { mockCardDS.setIsActivated(true) }
    }
}