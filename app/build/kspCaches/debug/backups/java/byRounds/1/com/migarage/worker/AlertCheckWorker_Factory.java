package com.migarage.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import com.migarage.domain.repository.AlertRepository;
import com.migarage.domain.repository.DocumentRepository;
import dagger.internal.DaggerGenerated;
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
public final class AlertCheckWorker_Factory {
  private final Provider<DocumentRepository> documentRepositoryProvider;

  private final Provider<AlertRepository> alertRepositoryProvider;

  public AlertCheckWorker_Factory(Provider<DocumentRepository> documentRepositoryProvider,
      Provider<AlertRepository> alertRepositoryProvider) {
    this.documentRepositoryProvider = documentRepositoryProvider;
    this.alertRepositoryProvider = alertRepositoryProvider;
  }

  public AlertCheckWorker get(Context context, WorkerParameters workerParams) {
    return newInstance(context, workerParams, documentRepositoryProvider.get(), alertRepositoryProvider.get());
  }

  public static AlertCheckWorker_Factory create(
      Provider<DocumentRepository> documentRepositoryProvider,
      Provider<AlertRepository> alertRepositoryProvider) {
    return new AlertCheckWorker_Factory(documentRepositoryProvider, alertRepositoryProvider);
  }

  public static AlertCheckWorker newInstance(Context context, WorkerParameters workerParams,
      DocumentRepository documentRepository, AlertRepository alertRepository) {
    return new AlertCheckWorker(context, workerParams, documentRepository, alertRepository);
  }
}
