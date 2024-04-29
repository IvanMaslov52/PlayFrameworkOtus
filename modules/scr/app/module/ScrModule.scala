package module

import di.AppModule
import models.repository.{ProductItemRepository, ProductItemRepositoryImpl, ProductRepository, ProductRepositoryImpl}
import models.services.services.{LogService, LogServiceImpl}
import models.services.{ProductService, ProductServiceImpl}

class ScrModule extends AppModule{
  override def configure(): Unit = {
    bindSingleton[LogService, LogServiceImpl]
    bindSingleton[ProductService, ProductServiceImpl]
    bindSingleton[ProductRepository, ProductRepositoryImpl]
    bindSingleton[ProductItemRepository, ProductItemRepositoryImpl]
  }
}
