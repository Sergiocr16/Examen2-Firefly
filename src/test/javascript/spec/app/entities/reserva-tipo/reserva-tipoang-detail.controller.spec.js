'use strict';

describe('Controller Tests', function() {

    describe('ReservaTipo Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockPreviousState, MockReservaTipo, MockTipo, MockReserva;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockPreviousState = jasmine.createSpy('MockPreviousState');
            MockReservaTipo = jasmine.createSpy('MockReservaTipo');
            MockTipo = jasmine.createSpy('MockTipo');
            MockReserva = jasmine.createSpy('MockReserva');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity,
                'previousState': MockPreviousState,
                'ReservaTipo': MockReservaTipo,
                'Tipo': MockTipo,
                'Reserva': MockReserva
            };
            createController = function() {
                $injector.get('$controller')("ReservaTipoAngDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'coffeeShopApp:reservaTipoUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
