package com.migarage.di;

import com.migarage.data.repository.AlertRepositoryImpl;
import com.migarage.domain.repository.AlertRepository;
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
public final class AppModule_ProvideAlertRepositoryFactory implements Factory<AlertRepository> {
  private final Provider<AlertRepositoryImpl> implProvider;

  public AppModule_ProvideAlertRepositoryFactory(Provider<AlertRepositoryImpl> implProvider) {
    this.implProvider = implProvider;
  }

  @Override
  public AlertRepository get() {
    return provideAlertRepository(implProvider.get());
  }

  public static AppModule_ProvideAlertRepositoryFactory create(
      Provider<AlertRepositoryImpl> implProvider) {
    return new AppModule_ProvideAlertRepositoryFactory(implProvider);
  }

  public static AlertRepository provideAlertRepository(AlertRepositoryImpl impl) {
    return Preconditions.checkNotNullFromProvides(AppModule.INSTANCE.provideAlertRepository(impl));
  }
}
