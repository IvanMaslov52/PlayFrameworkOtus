package module

import di.AppModule
import models.services.{ProductService, ProductServiceImpl}

class ScrModule extends AppModule{
  override def configure(): Unit = {
    bindSingleton[ProductService, ProductServiceImpl]
  }
}
