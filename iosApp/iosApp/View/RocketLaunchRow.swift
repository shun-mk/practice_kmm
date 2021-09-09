//
//  RocketLaunchRow.swift
//  iosApp
//
//  Created by Shunsaku Miki on 2021/09/10.
//  Copyright © 2021 orgName. All rights reserved.
//

import SwiftUI
import shared

struct RocketLaunchRow: View {
    var rocketLaunch: RocketLaunchData

    private var launchText: String {
        guard let isSuccess = rocketLaunch.launchSuccess?.boolValue else {
            return "No Data"
        }
        return isSuccess ? "Successful" : "Unsaccessful"
    }

    private var launchColor: Color {
        guard let isSccess = rocketLaunch.launchSuccess?.boolValue else {
            return .gray
        }
        return isSccess ? .green : .red
    }

    var body: some View {
        HStack() {
            VStack(alignment: .leading, spacing: 10.0, content: {
                Text("Launch name: \(rocketLaunch.missionName)")
                Text(launchText).foregroundColor(launchColor)
                Text("Launch year: \(rocketLaunch.launchYear)")
                Text("Launch details: \(rocketLaunch.details ?? "")")
            })
        }
    }
}
