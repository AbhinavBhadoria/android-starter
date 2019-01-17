package bhadoria.abhinav.domain.repository.factory

import bhadoria.abhinav.domain.repository.IGithubRepository


interface  IGithubRepositoryFactory{

    fun getRemote(): IGithubRepository

    fun getLocal(): IGithubRepository

    fun getRepository(): IGithubRepository
}