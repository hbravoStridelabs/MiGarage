package com.migarage.di;

import com.migarage.data.local.db.MiGarageDatabase;
import com.migarage.data.local.db.dao.DocumentDao;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata
@QualifierMetadata
@DaggerGenerated
@Generated(
    value = "dagger.internal.codegen.ComponentProcessor",
    comments = "https://dagger.dev"
)
@SuppressWarnings({
    "unchecked",
    "rawtypes",
    "KotlinInternal",
    "KotlinInternalInJava"
})
public final class AppModule_ProvideDocumentDaoFactory implements Factory<DocumentDao> {
  private final Provider<MiGarageDatabase> dbProvider;

  public AppModule_ProvideDocumentDaoFactory(Provider<MiGarageDatabase> dbProvider) {
    this.dbProvider = dbProvider;
  }

  @Override
  public DocumentDao get() {
    return provideDocumentDao(dbProvider.get());
  }

  public static AppModule_ProvideDocumentDaoFactory create(Provider<MiGarageDatabase> dbProvider) {
    return new AppModule_ProvideDocumentDaoFactory(dbProvider);
  }

  public static DocumentDao provideDocumentDao(MiGarageDatabase db) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideDocumentDao(db));
  }
}
