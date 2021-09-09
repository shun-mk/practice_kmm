//
//  RocketLaunchViewModel.swift
//  iosApp
//
//  Created by Shunsaku Miki on 2021/09/10.
//  Copyright © 2021 orgName. All rights reserved.
//

import shared

final class RocketLaunchViewModel: ObservableObject {
    enum LoadingState {
        case loading
        case result([RocketLaunchData])
        case error(String)
    }

    private let sdk: SpaceXSDK
    @Published var launches = LoadingState.loading

    init(sdk: SpaceXSDK) {
        self.sdk = sdk
        loadLaunches(forceReload: false)
    }

    func loadLaunches(forceReload: Bool) {
        sdk.getLaunches(forceReload: forceReload) { [weak self] data, error in
            guard let data = data else {
                self?.launches = .error(error?.localizedDescription ?? "Unknown error")
                return
            }
            self?.launches = .result(data)
        }
    }
}

extension RocketLaunchData: Identifiable { }
