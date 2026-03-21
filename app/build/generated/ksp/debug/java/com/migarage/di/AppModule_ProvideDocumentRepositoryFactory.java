package com.migarage.di;

import com.migarage.data.repository.DocumentRepositoryImpl;
import com.migarage.domain.repository.DocumentRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
import dagger.internal.Preconditions;
import dagger.internal.QualifierMetadata;
import dagger.internal.ScopeMetadata;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

@ScopeMetadata("javax.inject.Singleton")
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
public final class AppModule_ProvideDocumentRepositoryFactory implements Factory<DocumentRepository> {
  private final Provider<DocumentRepositoryImpl> implProvider;

  public AppModule_ProvideDocumentRepositoryFactory(Provider<DocumentRepositoryImpl> implProvider) {
    this.implProvider = implProvider;
  }

  @Override
  public DocumentRepository get() {
    return provideDocumentRepository(implProvider.get());
  }

  public static AppModule_ProvideDocumentRepositoryFactory create(
      Provider<DocumentRepositoryImpl> implProvider) {
    return new AppModule_ProvideDocumentRepositoryFactory(implProvider);
  }

  public static DocumentRepository provideDocumentRepository(DocumentRepositoryImpl impl) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideDocumentRepository(impl));
  }
}
