package bhadoria.abhinav.presentation.model.mapper

interface IPresentationToDataMapper<Presentation, Data> {

    fun mapToPresentation(data: Data): Presentation

    fun mapToData(presentation: Presentation): Data
}