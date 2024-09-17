package eu.petrfaruzel.scratcher.features.activation.data

import de.jensklingenberg.ktorfit.Response
import de.jensklingenberg.ktorfit.http.GET
import de.jensklingenberg.ktorfit.http.Query

interface ActivationApi {

    @GET("/version")
    suspend fun getVersion(@Query("code") code: String): Response<VersionDTO>

}