package com.migarage.presentation.screens.documents;

import com.migarage.domain.repository.DocumentRepository;
import dagger.internal.DaggerGenerated;
import dagger.internal.Factory;
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
public final class DocumentsViewModel_Factory implements Factory<DocumentsViewModel> {
  private final Provider<DocumentRepository> repositoryProvider;

  public DocumentsViewModel_Factory(Provider<DocumentRepository> repositoryProvider) {
    this.repositoryProvider = repositoryProvider;
  }

  @Override
  public DocumentsViewModel get() {
    return newInstance(repositoryProvider.get());
  }

  public static DocumentsViewModel_Factory create(Provider<DocumentRepository> repositoryProvider) {
    return new DocumentsViewModel_Factory(repositoryProvider);
  }

  public static DocumentsViewModel newInstance(DocumentRepository repository) {
    return new DocumentsViewModel(repository);
  }
}
