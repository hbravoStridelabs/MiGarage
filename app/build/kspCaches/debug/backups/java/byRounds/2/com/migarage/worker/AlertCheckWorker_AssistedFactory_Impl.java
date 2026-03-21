package com.migarage.worker;

import android.content.Context;
import androidx.work.WorkerParameters;
import dagger.internal.DaggerGenerated;
import dagger.internal.InstanceFactory;
import javax.annotation.processing.Generated;
import javax.inject.Provider;

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
public final class AlertCheckWorker_AssistedFactory_Impl implements AlertCheckWorker_AssistedFactory {
  private final AlertCheckWorker_Factory delegateFactory;

  AlertCheckWorker_AssistedFactory_Impl(AlertCheckWorker_Factory delegateFactory) {
    this.delegateFactory = delegateFactory;
  }

  @Override
  public AlertCheckWorker create(Context p0, WorkerParameters p1) {
    return delegateFactory.get(p0, p1);
  }

  public static Provider<AlertCheckWorker_AssistedFactory> create(
      AlertCheckWorker_Factory delegateFactory) {
    return InstanceFactory.create(new AlertCheckWorker_AssistedFactory_Impl(delegateFactory));
  }

  public static dagger.internal.Provider<AlertCheckWorker_AssistedFactory> createFactoryProvider(
      AlertCheckWorker_Factory delegateFactory) {
    return InstanceFactory.create(new AlertCheckWorker_AssistedFactory_Impl(delegateFactory));
  }
}
